/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.sra.service;

import com.elixir.biohackaton.ISAToSRA.model.Study;
import java.util.List;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

@Service
public class SRAStudyXmlCreator {
  public void createENAStudySetElement(final Element webinElement, final List<Study> studies) {
    try {
      final Element studySetElement = webinElement.addElement("STUDY_SET");

      studies.forEach(
          study -> {
            final Element studyElement =
                studySetElement.addElement("STUDY").addAttribute("alias", study.getTitle());
            final Element studyDescriptorElement = studyElement.addElement("DESCRIPTOR");

            studyDescriptorElement.addElement("STUDY_TITLE").addText(study.getTitle());
            studyDescriptorElement.addElement("STUDY_DESCRIPTION").addText(study.getDescription());
            studyDescriptorElement.addElement("STUDY_ABSTRACT").addText(study.getDescription());
            studyDescriptorElement
                .addElement("STUDY_TYPE")
                .addAttribute("existing_study_type", "Other");

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
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
