import json as Json

from xml_provider.xml_provider import XmlProvider


class DataFilesToRunConvertor:

    def __init__(self):
        self._import_path = None
        self._isa_json = None
        self._current_data_file = None
        self._data_files = None

        self.experiment_reference = None
        pass


    def get_data_files(self):
        data_files = []
        studies = self._isa_json["investigation"]["studies"]
        for study in studies:
            assays = study["assays"]
            for assay in assays:
                data_files.extend(assay["dataFiles"])
                
        return data_files


    @property
    def import_path(self):
        return self._import_path

    @import_path.setter
    def import_path(self, path):
        self._import_path = path
        with open(path) as isa_file:
            self._isa_json = Json.load(isa_file)
            self._data_files = self.get_data_files()
        pass


    def validate(self):
        if self._is_empty(self.experiment_reference):
            raise ValueError("Experiment reference number is needed in dataFiles")

        for data_file in self._data_files:
            if self._is_empty(data_file["name"]):
                raise ValueError("name is needed in dataFiles")
            
            requirment_comments = ["file checksum"]
            not_fulfilled_requirement_comments = requirment_comments.copy()
            for comment in data_file["comments"]:
                key = comment["name"]
                value = comment["value"]
                if key in requirment_comments and not self._is_empty(value):
                    not_fulfilled_requirement_comments.remove(key)
            if len(not_fulfilled_requirement_comments) > 0:
                raise ValueError(f"{not_fulfilled_requirement_comments} is/are needed in dataFiles comments")
        pass


    def convert(self):
        self.validate()

        run_set = self.run_set_xml()
        for data_file in self._data_files:
            self._current_data_file = data_file
            run_set.add(
                self.run_xml() \
                    .add(self.title_xml()) \
                    .add(self.experiment_ref_xml()) \
                    .add(
                            self.data_block_xml() \
                                .add(self.files_xml())
                        )  
            )        

        return run_set


    def run_set_xml(self):
        run_set = XmlProvider("RUN_SET")

        return run_set


    def run_xml(self):
        run = XmlProvider("RUN")
        run.set_attribute("alias", self._current_data_file["@id"])

        return run


    def title_xml(self):
        title = XmlProvider("TITLE")
        title.set_text(self._current_data_file["name"])

        return title


    def experiment_ref_xml(self):
        experiment_ref = XmlProvider("EXPERIMENT_REF")
        experiment_ref.set_attribute("refname", self.experiment_reference)

        return experiment_ref


    def data_block_xml(self):
        data_block = XmlProvider("DATA_BLOCK")

        return data_block


    def files_xml(self):
        files = XmlProvider("FILES")

        file = XmlProvider("FILE")
        file.parent = files
        file.set_attribute("filename", self._current_data_file["name"])
        for comment in self._current_data_file["comments"]:
            name = comment["name"]
            value = comment["value"]
            if name == "file type":
                file.set_attribute("filetype", value)
            elif name == "file checksum":
                file.set_attribute("checksum", value)

        return files


    def _is_empty(self, text: str):
        return not (text and text.strip())