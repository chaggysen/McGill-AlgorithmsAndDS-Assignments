memo = [[float("inf") for i in range(6)] for j in range(6)]
print(memo)


def find_min(array, start, end, prev_jump):
    if start < 0 or start > end or end >= len(array):
        return float("inf")
    if memo[prev_jump][start] != float("inf"):
        return memo[prev_jump][start]
    if start == end:
        return array[end]
    cur_jump = prev_jump + 1
    forward = find_min(array, start + cur_jump, end, cur_jump)
    if start != 0:
        backward = find_min(array, start - prev_jump, end, prev_jump)
    else:
        backward = float("inf")
    path = min(forward, backward)
    if start == 0 and prev_jump == 0:
        sum = forward
        if memo[prev_jump][start] != float("inf"):
            memo[prev_jump][start] = min(memo[prev_jump][start], sum)
        else:
            memo[prev_jump][start] = sum
    else:
        sum = path + array[start]
        if memo[prev_jump][start] != float("inf"):
            memo[prev_jump][start] = min(memo[prev_jump][start], sum)
        else:
            memo[prev_jump][start] = sum
    # memo[start] = min(memo[start], sum)
    return memo[prev_jump][start]


arr = [1, 2, 3, 4, 5, 6]
result = find_min(arr, 0, len(arr) - 1, 0)
print(str(result))
