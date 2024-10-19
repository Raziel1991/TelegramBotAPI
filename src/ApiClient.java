import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
abstract class ApiClient {
    protected String apiKey;

    public ApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    // Abstract method to get the specific URI for each API client
    abstract URI getUri() throws Exception;

    public String getApiResponse(){
        String response = null;
        try{
            URI uri = getUri();
            URL url = uri.toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    content.append(line);
                }
                in.close();
                response = content.toString();
            } else {
                System.err.println("Error: " + connection.getResponseCode());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
