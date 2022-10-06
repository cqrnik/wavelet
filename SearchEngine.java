import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strs = new ArrayList<>();

    public String handleRequest(URI url) {

        System.out.println("Path: " + url.getPath());
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");// splits at equals sign

            if (parameters[0].equals("s")) {
                strs.add(parameters[1]);
                System.out.print(strs.size());
                return String.format("The string that was added is %s", parameters[1]);
            } else
                return "404 Not Found!";
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");// splits at equals sign

            if (parameters[0].equals("s")) {

                ArrayList<String> searchStrs = new ArrayList<>();
                for (int i = 0; i < strs.size(); i++) {
                    if ((strs.get(i)).contains(parameters[1])) {// if statement doesnt go through

                        searchStrs.add(strs.get(i));
                    }
                }
                String returnString = "The strings with the given substring are:\n";
                for (int i = 0; i < searchStrs.size(); i++) {
                    returnString += searchStrs.get(i);
                    returnString += "\n";
                }
                return returnString;
            } else
                return "404 Not Found!";
        } else if (url.getPath().equals("/")) {
            return ("hi");
        } else
            return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
