package de.ad.android.tools.lint.test;

import com.android.tools.lint.LintCliClient;
import com.android.tools.lint.LintCliFlags;
import com.android.tools.lint.TextReporter;
import com.android.tools.lint.client.api.IssueRegistry;
import de.ad.android.tools.lint.CustomIssueRegistry;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class DetectorTest {
  
  public String analyze(String... relativePaths) throws Exception {
    TestLintClient testLintClient = new TestLintClient();
    return testLintClient.analyze(relativePaths);
  }
  
  /**
   * https://android.googlesource.com/platform/tools/base/+/master/lint/libs/lint-tests/src/main/java/com/android/tools/lint/checks/infrastructure/LintDetectorTest.java
   */
  private static class TestLintClient extends LintCliClient {
    private StringWriter mWriter = new StringWriter();

    public TestLintClient() {
      super(new LintCliFlags());
      mFlags.getReporters().add(new TextReporter(this, mFlags, mWriter, false));
    }

    public String analyze(String... relativePaths) throws IOException {
      List<File> files = getFiles(relativePaths);
      run(new CustomIssueRegistry(), files);
      
      return mWriter.toString();
    }

    private List<File> getFiles(String... relativePaths) {
      List<File> files = new ArrayList<File>();
      for(String relativePath : relativePaths){
        ClassLoader classLoader = TestLintClient.class.getClassLoader();
        files.add(new File(classLoader.getResource(relativePath).getFile()));
      }
      
      return files;
    }

    @Override public int run(IssueRegistry registry, List<File> files)
        throws IOException {
      return super.run(registry, files);
    }
  }
}
