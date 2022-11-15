import argparse
from converter.data_files import DataFilesToRunConvertor


def check_arguments():
    global args
    if args.importpath is None:
        raise ValueError("Import file path is needed, try --importpath.")
    if args.exportpath is None:
        raise ValueError("Export file path is needed, try --exportpath.")
    if args.ref is None:
        raise ValueError("Experiment reference is needed, try --ref.")

def set_arguments():
    arg_parser = argparse.ArgumentParser()
    arg_parser.add_argument("--importpath", help="isa json file")
    arg_parser.add_argument("--exportpath", help="ena xml file")
    arg_parser.add_argument("--ref", help="experiment reference")
    global args
    args = arg_parser.parse_args()

if __name__ == "__main__":
    try:
        global args
        set_arguments()
        check_arguments()
        
        convertor = DataFilesToRunConvertor()
        convertor.experiment_reference = args.ref
        convertor.import_path = args.importpath
        convertor.convert().export(args.exportpath)
    except ValueError as e:
        print(f"Input error: {e}")
    else:
        print("Mapping dataFiles from ISA to RUN_SET in ENA is done successfully!")