import subprocess

def runmytestcases(typeofnum,operation,num1,num2):
    try:
        result = subprocess.run(["java", "-jar","arbitraryarithmetic/aarithmetic.jar",typeofnum,operation,num1,num2],
                            capture_output=True, text=True)
        return result.stdout.strip()
    except Exception as e:
        return f"error is {str(e)}"
        
def main():
    with open("TEST CASES/input.txt") as fin,open ("TEST CASES/got_output.txt","w") as fout:
        cases = [line.strip().split() for line in fin]
        
        for case in cases:
            if len(case) != 4:
                fout.write("invalid test case format")
                continue
            typeinp,op,n1,n2 = case
            output = runmytestcases(typeinp,op,n1,n2)
            fout.write(output + "\n")
            
        

if __name__ == "__main__":
    main()