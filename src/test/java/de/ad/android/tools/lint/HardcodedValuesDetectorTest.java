package de.ad.android.tools.lint;

import com.ad.android.tools.lint.Lint;
import com.android.tools.lint.Warning;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HardcodedValuesDetectorTest {
  @Rule public Lint lint = new Lint();
  
  @Test
  public void test() throws Exception {
    lint.setFiles("res/layout/test.xml");
    lint.setIssues(HardcodedValuesDetector.ISSUE);
    
    lint.analyze();
    
    List<Warning> warnings = lint.getWarnings();
    
    assertThat(warnings).hasSize(2);
    
    assertThat(warnings.get(0).message).isEqualTo(
        "[I18N] Hardcoded string \"Hardcoded text\", should use `@string` resource or placeholder `:string`");
    assertThat(warnings.get(0).file.getPath()).endsWith("res/layout/test.xml");
    assertThat(warnings.get(0).line).isEqualTo(14);

    assertThat(warnings.get(1).message).isEqualTo(
        "[I18N] Hardcoded string \"Hardcoded hint\", should use `@string` resource or placeholder `:string`");
    assertThat(warnings.get(1).file.getPath()).endsWith("res/layout/test.xml");
    assertThat(warnings.get(1).line).isEqualTo(36);
  }
}
