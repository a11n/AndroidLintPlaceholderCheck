package de.ad.android.tools.lint;

import com.android.annotations.NonNull;
import com.android.tools.lint.checks.HardcodedValuesDetector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.TextFormat;
import com.android.tools.lint.detector.api.XmlContext;
import org.w3c.dom.Attr;

import static com.android.SdkConstants.ANDROID_URI;

public class ExtendedHardcodedValuesDetector extends HardcodedValuesDetector {

  public static final Issue ISSUE = Issue.create(
      "HardcodedTextWithPlaceholder",
      HardcodedValuesDetector.ISSUE.getBriefDescription(TextFormat.TEXT),
      HardcodedValuesDetector.ISSUE.getExplanation(TextFormat.TEXT),
      HardcodedValuesDetector.ISSUE.getCategory(),
      HardcodedValuesDetector.ISSUE.getPriority(),
      HardcodedValuesDetector.ISSUE.getDefaultSeverity(),
      new Implementation(
          ExtendedHardcodedValuesDetector.class,
          HardcodedValuesDetector.ISSUE.getImplementation().getScope()));

  @Override
  public void visitAttribute(@NonNull XmlContext context,
      @NonNull Attr attribute) {
    String value = attribute.getValue();
    if (!value.isEmpty() && (value.charAt(0) != '@'
        && value.charAt(0) != '?'
        && value.charAt(0) != ':')) {
      // Make sure this is really one of the android: attributes
      if (!ANDROID_URI.equals(attribute.getNamespaceURI())) {
        return;
      }

      context.report(ISSUE, attribute, context.getLocation(attribute),
          String.format(
              "[I18N] Hardcoded string \"%1$s\", should use `@string` resource or placeholder `:string`",
              value));
    }
  }
}