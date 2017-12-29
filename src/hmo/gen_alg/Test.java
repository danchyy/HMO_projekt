package hmo.gen_alg;

public class Test {

    private byte[] array;

    private String name;

    private int length;

    private String[] availableMachines;

    private String[] availableResources;

    public Test(String name, int length, String[] availableMachines, String[] availableResources) {
        this.name = name;
        this.length = length;
        this.availableMachines = availableMachines;
        this.availableResources = availableResources;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + ", ");
        sb.append(this.length);
        sb.append(", ");
        sb.append("[");
        for (String machine: availableMachines) {
            sb.append(machine);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("], [");
        for (String resource: availableResources) {
            sb.append(resource);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        return sb.toString();
    }
}
