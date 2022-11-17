/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IsaJsonWebinSubmissionApplication implements ApplicationRunner {
  @Autowired
  com.elixir.biohackaton.ISAToSRA.sra.service.SRAWebinSubmissionXmlCreator
      SRAWebinSubmissionXmlCreator;

  public static void main(final String[] args) {
    SpringApplication.run(IsaJsonWebinSubmissionApplication.class, args);
  }

  @Override
  public void run(final ApplicationArguments args) throws Exception {
    this.SRAWebinSubmissionXmlCreator.performSubmissionToBioSamplesAndEna(args);
  }
}
