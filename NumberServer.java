import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Number: %d", num); // if no path print number
        } else if (url.getPath().equals("/increment")) {
            num += 1; // if /increment then increment by 1
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());// prints path to console
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");// splits at equals sign
                if (parameters[0].equals("count")) {// query is ?count
                    num += Integer.parseInt(parameters[1]);// adds number after equals sign
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                } // /add?count=5
            }
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
