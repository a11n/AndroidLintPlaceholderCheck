package de.ad.android.tools.lint;

import de.ad.android.tools.lint.test.DetectorTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HardcodedValuesDetectorTest extends DetectorTest {
  @Test
  public void test() throws Exception {
    String result = analyze("res/layout/test.xml");
    
    assertThat(result).isEqualTo("res/layout/test.xml:15: Warning: [I18N] Hardcoded string \"Hardcoded text\", should use @string resource or placeholder :string [HardcodedTextWithPlaceholder]\n"
        + "      android:text=\"Hardcoded text\"\n"
        + "      ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
        + "res/layout/test.xml:37: Warning: [I18N] Hardcoded string \"Hardcoded hint\", should use @string resource or placeholder :string [HardcodedTextWithPlaceholder]\n"
        + "      android:layout_gravity=\"center_horizontal\" android:hint=\"Hardcoded hint\"\n"
        + "                                                 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
        + "0 errors, 2 warnings\n");
  }
}
