import os
import re

def process_java_file(file_path):
    with open(file_path, "r", encoding="utf-8") as f:
        content = f.read()

    # 正则：匹配 ((BlockCasings8) xxx.sBlockCasings8).getTextureIndex(10)
    # xxx. 可有可无
    pattern = re.compile(
        r'\(\(BlockCasings(\d+)\)\s*(?:\w+\.)?sBlockCasings\1\)\.getTextureIndex\((\d+)\)'
    )

    # 替换为 StructureUtils.getTextureIndex(sBlockCasings8, 10)
    new_content, count = pattern.subn(
        r'StructureUtils.getTextureIndex(sBlockCasings\1, \2)', content
    )

    if count > 0:
        with open(file_path, "w", encoding="utf-8") as f:
            f.write(new_content)
        print(f"Processed: {file_path}, Replaced: {count}")


def process_folder(root_folder):
    for root, _, files in os.walk(root_folder):
        for file in files:
            if file.endswith(".java"):
                process_java_file(os.path.join(root, file))


if __name__ == "__main__":
    folder = input("请输入要处理的文件夹路径: ").strip()
    process_folder(folder)
