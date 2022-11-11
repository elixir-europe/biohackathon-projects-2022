/** Elixir BioHackathon 2022 */
package com.elixir.biohackaton.ISAToSRA.biosamples;

public class BioSamplesProperties extends uk.ac.ebi.biosamples.BioSamplesProperties {

  @Override
  public int getBiosamplesClientPagesize() {
    return 1000;
  }

  @Override
  public int getBiosamplesClientTimeout() {
    return 60000;
  }

  @Override
  public int getBiosamplesClientConnectionCountMax() {
    return 8;
  }

  @Override
  public int getBiosamplesClientThreadCount() {
    return 1;
  }

  @Override
  public int getBiosamplesClientThreadCountMax() {
    return 8;
  }

  @Override
  public int getBiosamplesClientConnectionCountDefault() {
    return 8;
  }

  @Override
  public boolean getAgentSolrStayalive() {
    return false;
  }

  @Override
  public String getOls() {
    return "https://wwwdev.ebi.ac.uk/ols";
  }
}
