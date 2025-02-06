def merge_lines(file_path, output_path, n):
    with open(file_path, 'r') as file:
        lines = file.readlines()
    
    merged_lines = ['' for _ in range(n)]
    for i in range(len(lines)):
        merged_lines[i % n] += lines[i].rstrip('\n') + ','

    # 拼接每一行并添加逗号
    result_lines = ['' for _ in range(n)]
    for i in range(n):
        result_lines[i] = merged_lines[i].rstrip(',')

    # 写入新的文件
    with open(output_path, 'w') as new_file:
        for line in result_lines:
            new_file.write(line + '\n')

# 示例用法
merge_lines('output.txt', 'new_file.txt', 4)
