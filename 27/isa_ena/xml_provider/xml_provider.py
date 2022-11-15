from xml.etree.ElementTree import *
import os as Os


class XmlProvider(ElementTree):
    """
    xml provider
    """

    def __init__(self, tag_name):
        self._tag = Element(tag_name)
        self._parent = None
        pass


    def export(self, file_path):
        print(Os.path.dirname(file_path))
        Os.makedirs(Os.path.dirname(file_path), exist_ok=True)
        ena = ElementTree(self._tag)
        ena.write(file_path)
        pass

    @property
    def parent(self):
        return self._parent

    @parent.setter
    def parent(self, value:'XmlProvider'):
        value._tag.append(self._tag)
        pass

    def set_attribute(self, attribute, value):
        self._tag.set(attribute, value)
        pass

    def set_text(self, text):
        self._tag.text = text
        pass

    def add(self, xml_provider:'XmlProvider'):
        xml_provider.parent = self

        return self