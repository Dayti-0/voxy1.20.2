package me.cortex.voxy.client.core.model.bakery;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.cortex.voxy.client.core.gl.GlBuffer;
import me.cortex.voxy.client.core.gl.GlVertexArray;
import me.cortex.voxy.client.core.gl.shader.Shader;
import me.cortex.voxy.client.core.gl.shader.ShaderType;
import me.cortex.voxy.client.core.rendering.util.UploadStream;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL33.glBindSampler;
import static org.lwjgl.opengl.GL45.*;

public class BudgetBufferRenderer {
    public static final int VERTEX_FORMAT_SIZE = 24;

    private static final Shader bakeryShader = Shader.make()
            .add(ShaderType.VERTEX, "voxy:bakery/position_tex.vsh")
            .add(ShaderType.FRAGMENT, "voxy:bakery/position_tex.fsh")
            .compile();


    public static void init(){}
    private static final GlBuffer indexBuffer;
    static {
        // In 1.20.2, we need to generate the index buffer ourselves
        // For quads, we generate indices: 0,1,2, 2,3,0 pattern for each quad
        int maxQuads = 4096 * 3;
        indexBuffer = new GlBuffer(maxQuads * 6 * 2L); // 6 indices per quad, 2 bytes each (short)

        // Generate quad indices
        java.nio.ShortBuffer indices = org.lwjgl.BufferUtils.createShortBuffer(maxQuads * 6);
        for (int i = 0; i < maxQuads; i++) {
            int base = i * 4;
            indices.put((short) base);
            indices.put((short) (base + 1));
            indices.put((short) (base + 2));
            indices.put((short) (base + 2));
            indices.put((short) (base + 3));
            indices.put((short) base);
        }
        indices.flip();
        glNamedBufferSubData(indexBuffer.id, 0, indices);
    }

    private static final int STRIDE = 24;
    private static final GlVertexArray VA = new GlVertexArray()
            .setStride(STRIDE)
            .setF(0, GL_FLOAT, 4, 0)//pos, metadata
            .setF(1, GL_FLOAT, 2, 4 * 4)//UV
            .bindElementBuffer(indexBuffer.id);

    private static GlBuffer immediateBuffer;
    private static int quadCount;

    public static void drawFast(BufferBuilder.RenderedBuffer buffer, AbstractTexture tex, Matrix4f matrix) {
        var drawState = buffer.drawState();
        if (drawState.mode() != VertexFormat.Mode.QUADS) {
            throw new IllegalStateException("Fast only supports quads");
        }

        var buff = buffer.vertexBuffer();
        int size = buff.remaining();
        if (size%STRIDE != 0) throw new IllegalStateException();
        size /= STRIDE;
        if (size%4 != 0) throw new IllegalStateException();
        size /= 4;
        setup(MemoryUtil.memAddress(buff), size, tex.getId());
        buffer.release();

        render(matrix);
    }

    public static void setup(long dataPtr, int quads, int texId) {
        if (quads == 0) {
            throw new IllegalStateException();
        }

        quadCount = quads;

        long size = quads * 4L * STRIDE;
        if (immediateBuffer == null || immediateBuffer.size()<size) {
            if (immediateBuffer != null) {
                immediateBuffer.free();
            }
            immediateBuffer = new GlBuffer(size*2L);//This also accounts for when immediateBuffer == null
            VA.bindBuffer(immediateBuffer.id);
        }
        long ptr = UploadStream.INSTANCE.upload(immediateBuffer, 0, size);
        MemoryUtil.memCopy(dataPtr, ptr, size);
        UploadStream.INSTANCE.commit();

        bakeryShader.bind();
        VA.bind();
        glMemoryBarrier(GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT);
        glBindSampler(0, 0);
        glBindTextureUnit(0, texId);
    }

    public static void render(Matrix4f matrix) {
        glUniformMatrix4fv(1, false, matrix.get(new float[16]));
        glDrawElements(GL_TRIANGLES, quadCount * 2 * 3, GL_UNSIGNED_SHORT, 0);
    }
}
