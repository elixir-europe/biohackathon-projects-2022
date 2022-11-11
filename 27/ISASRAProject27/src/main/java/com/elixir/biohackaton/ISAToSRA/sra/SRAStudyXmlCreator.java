/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra;

import com.elixir.biohackaton.ISAToSRA.model.IsaJson;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service
public class SRAStudyXmlCreator {
  public String create(final Element webinElement, final IsaJson isaJson) {
    try {
      final Element studySetElement = webinElement.addElement("STUDY_SET");

      isaJson
          .getInvestigation()
          .getStudies()
          .forEach(
              study -> {
                final Element studyElement = studySetElement.addElement("STUDY");

                studyElement.addAttribute("alias", "test-isa-study");

                final Element studyDescriptorElement = studyElement.addElement("DESCRIPTOR");

                studyDescriptorElement.addElement("STUDY_TITLE").addText(study.getTitle());
                studyDescriptorElement
                    .addElement("STUDY_DESCRIPTION")
                    .addText(study.getDescription());
                studyDescriptorElement.addElement("STUDY_ABSTRACT").addText(study.getDescription());

                final Element studyAttributesElement = studyElement.addElement("STUDY_ATTRIBUTES");

                study
                    .getComments()
                    .forEach(
                        comment -> {
                          final Element studyAttributeElement =
                              studyAttributesElement.addElement("STUDY_ATTRIBUTE");

                          studyAttributeElement.addElement("TAG").addText(comment.getName());
                          studyAttributeElement
                              .addElement("VALUE")
                              .addText((String) comment.getValue());
                        });
              });

      return "test-isa-study";
    } catch (final Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
