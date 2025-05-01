import os,sys,subprocess

def main():
    if len(sys.argv) != 5:
        print("Please enter input in format of <int/float> <add/sub/mul/div> <First operand> <Second operand>")
        return
    
    os.system("ant compile")
    command = ["java" , "-cp" , "build", "MyInfArith"] + sys.argv[1:]
    subprocess.run(command)
if __name__ == "__main__":
    main()