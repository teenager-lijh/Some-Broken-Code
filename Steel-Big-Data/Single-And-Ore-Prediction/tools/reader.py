import csv


class CsvReader:
    def __init__(self, file_name, encoding='UTF-8'):
        self.encoding = encoding
        self.all_data = []
        self.file_name = file_name

        csv_reader = csv.reader(open(file_name, encoding=self.encoding))
        for row in csv_reader:
            self.all_data.append(row)

    def get_all_data(self):
        return self.all_data


