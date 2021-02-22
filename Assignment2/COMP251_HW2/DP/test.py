def find_min(array, start, end, prev_jump):
    # print("start " + str(start))
    # print("end " + str(end))
    # print("prev_jump " + str(prev_jump))
    # print("===========")
    if start < 0 or start > end:
        return float("inf")
    if end >= len(array):
        return float("inf")
    if start == end:
        return array[end]
    cur_jump = prev_jump + 1
    forward = find_min(array, start + cur_jump, end, cur_jump)
    if start != 0:
        backward = find_min(array, start - prev_jump, end, prev_jump)
    else:
        backward = float("inf")
    if forward < backward:
        if start == 0 and prev_jump == 0:
            sum = forward
        else:
            sum = forward + array[start]
            # print("sum using forward")
            # print("sum " + str(sum))
    elif backward < forward:
        if start == 0 and prev_jump == 0:
            sum = forward
        else:
            sum = backward + array[start]
            # print("sum using backward")
            # print("sum " + str(sum))
    return sum


arr = [1, 2, 3, 4, 5, 6]
result = find_min(arr, 0, len(arr) - 1, 0)
print(str(result))
