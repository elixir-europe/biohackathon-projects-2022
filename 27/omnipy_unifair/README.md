# Omnipy (uniFAIR) extension to flatten complex nested JSON

As an alternative implementation of the ISA JSON to ENA data brokering tool,
Sveinung Gundersen (ELIXIR-NO, UiO) explored the possibilities of extending
the [omnipy](https://github.com/fairtracks/omnipy) Python library (at the 
time of the Biohackathon named 'uniFAIR') with related functionality. The
library was under heavy development at the time and was not ready to be
used for the main implementation. It is now (Feb 2023) more mature and stable
and can be considered as a basis of similar developments.

The feature that was developed and showcased under the 2022 Biohackathon was
a data flow that transforms a set of nested JSON documents into relational
tables, in the form of both CSV files and Pandas DataFrames.

The code developed in the Biohackathon is deposited here, in its initial
version. The code will not run without the rest of the omnipy (or really
uniFAIR) code base. However, an updated implementation is now available in the 
[omnipy-examples PYPI package](https://pypi.org/project/omnipy-examples/),
which can easily be run from the command line, as follows:

- Make sure you have Python 3.10 installed, possibly via Conda (for now, 
  the version requirements of `omnipy` will be lowered later.). It is 
  recommended that a separate virtual environment is used, as `omnipy`
  packs a large number of dependencies. E.g:
  - `conda create -n omnipy python=3.10`
  - `conda activate omnipy`
- Install the `omnipy-examples` package:
  - `pip install omnipy-examples`
- Run the ISA-JSON example:
  - `omnipy-examples isajson`
- The output should appear in a new `data` directory, as compressed 
  directories. 
  - It is recommended to install a file viewer that are capable of browsing tar.gz files. 
    For instance, the "File Expander" plugin in PyCharm is excellent for this.
  - Alternatively, to unpack the compressed files of a run on the command line 
    (just make sure to replace the datetime string from this example): 

```
for f in $(ls data/2023_02_03-12_51_51/*.tar.gz); do mkdir ${f%.tar.gz}; tar xfzv $f -C ${f%.tar.gz}; done
```

Please refer to the documentation of [omnipy-examples](https://github.com/fairtracks/omnipy_examples) and 
[omnipy](https://github.com/fairtracks/omnipy) for more information.

(Note: the same ISA-JSON input files that are published in this repository is also made available in the
[omnipy-example-data](https://github.com/fairtracks/omnipy_example_data) repository/package, in order to
make the example run.)
