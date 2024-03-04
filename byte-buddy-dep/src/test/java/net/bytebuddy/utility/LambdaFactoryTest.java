package net.bytebuddy.utility;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.instrument.ClassFileTransformer;
import java.util.*;

import net.bytebuddy.agent.builder.LambdaFactory;


public class LambdaFactoryTest {

    @Test
    public void testRegister() {
        // Mock classFileTransformer and classFileFactory
        ClassFileTransformer classFileTransformer = createMockClassFileTransformer();
        Object classFileFactory = createMockClassFileFactory();

        // Register the classFileTransformer
        boolean isFirstTransformer = LambdaFactory.register(classFileTransformer, classFileFactory);

        // Ensure that registration is successful
        assertTrue(isFirstTransformer);

        // Ensure that the classFileTransformer is registered
        assertTrue(LambdaFactory.CLASS_FILE_TRANSFORMERS.containsKey(classFileTransformer));
    }

    @Test
    public void testRelease() {
        // Register a dummy class file transformer for testing
        ClassFileTransformer classFileTransformer = createMockClassFileTransformer();
        Object classFileFactory = createMockClassFileFactory();
        LambdaFactory.register(classFileTransformer, classFileFactory);

        // Check if the transformer is registered
        assertTrue(LambdaFactory.CLASS_FILE_TRANSFORMERS.containsKey(classFileTransformer));

        // Release the transformer
        boolean released = LambdaFactory.release(classFileTransformer);

        // Check if the transformer is released
        assertTrue(released);
        assertFalse(LambdaFactory.CLASS_FILE_TRANSFORMERS.containsKey(classFileTransformer));
    }

    // Helper methods to create mock objects
    private ClassFileTransformer createMockClassFileTransformer() {
        return new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    java.security.ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                String classContent = new String(classfileBuffer);
                classContent = classContent.replaceAll("Hello", "Hi");
                return classContent.getBytes();
            }
        };
    }

    private Object createMockClassFileFactory() {
        return new Object() {
            public byte[] make(Object caller, String invokedName, Object invokedType, Object samMethodType,
                               Object implMethod, Object instantiatedMethodType, boolean serializable,
                               List<Class<?>> markerInterfaces, List<?> additionalBridges,
                               Collection<ClassFileTransformer> classFileTransformers) {
                return new byte[0];
            }
        };
    }

}