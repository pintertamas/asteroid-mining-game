public class TestLogger {
    public void functionCalled(String className, String id, String funcName, String param, String returnType, int index) {
        index++;
        for (int i = 0; i < index; i++)
            System.out.print("\t");
        System.out.print(index + " ");
        System.out.println(className + " " + id + funcName + "(" + param + "): " + returnType);

        /* Template:
        String param = this.getClass().getName() + " " + this + " funcName(param): returnType"; */
    }

    public void functionReturned(String className, String id, String funcName, String param, String returned, int index) {
        index++;
        for (int i = 0; i < index; i++)
            System.out.print("\t");
        System.out.print(index + " ");
        System.out.println(className + " " + id + funcName + "(" + param + ") returned " + (returned.equals("") ? "" : ": " + returned));
    }
}
