/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples;

public enum BioSampleClientEnvironment {
  prod("https://www.ebi.ac.uk/biosamples/"),
  dev("https://wwwdev.ebi.ac.uk/biosamples/"),
  test("https://wwwdev.ebi.ac.uk/biosamples/"),
  /*
  Used for unit tests, TODO: decouple
   */
  fake("https://wwwdev.ebi.ac.uk/biosamples/test/");

  private final String uri;

  BioSampleClientEnvironment(String uri) {
    this.uri = uri;
  }

  public String uri() {
    return uri;
  }
}
