package net.bytebuddy.build.maven;

import static org.junit.Assert.assertTrue;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.internal.impl.DefaultRepositorySystem;
import org.junit.Test;

public class ByteBuddyMojoExecuteTest {
    /**
     * Method under test: {@link ByteBuddyMojo#execute()}
     */
    @Test
    public void testExecute() throws MojoExecutionException, MojoFailureException {
        // Arrange
        ByteBuddyMojo.ForProductionTypes.WithExtendedDependencies withExtendedDependencies = new ByteBuddyMojo.ForProductionTypes.WithExtendedDependencies();
        withExtendedDependencies.discovery = Discovery.ALL;
        withExtendedDependencies.transformations = null;
        withExtendedDependencies.context = null;
        withExtendedDependencies.project = new MavenProject();
        withExtendedDependencies.skip = false;
        withExtendedDependencies.incremental = false;
        withExtendedDependencies.repositorySystem = new DefaultRepositorySystem();
        withExtendedDependencies.classPathDiscovery = false;

        // Act
        withExtendedDependencies.execute();

        // Assert
        assertTrue(withExtendedDependencies.getLog() instanceof SystemStreamLog);
    }
}
