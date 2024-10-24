import os
path = '/Users/anwaralabbas/Downloads/Lab Simple SELECT and Sorting Data Download Jul 2, 2024 1028 AM-2'
files = os.listdir(path)

for index, file in enumerate(files):
    os.rename(os.path.join(path, file), os.path.join(path, ''.join([str(index), '.sql'])))