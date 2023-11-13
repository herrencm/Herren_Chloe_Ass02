public class Product {
    private String name;
    private String description;
    private String ID;

    public String formatName() {
        return String.format("%-35s", name);
    }

    public String formatDescription() {
        return String.format("%-75s", description);
    }

    public String formatID() {
        return String.format("%-6s", ID);
    }

    public void parseName(String formattedName) {
        this.name = formattedName.trim();
    }

    public void parseDescription(String formattedDescription) {
        this.description = formattedDescription.trim();
    }

    public void parseID(String formattedID) {
        this.ID = formattedID.trim();
    }
}
