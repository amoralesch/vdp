# Get Node Status Operation

In order to know if a node is up, any client (person or machine) can
ping the server to verify its status.

The entry point for this ping is the URL `/api/status`. If the server is
up and running, it will reply with a `Server Up` response.

### [Example](- "server-up")

When a client makes a _[GET](- "#method")_ **[/api/status](- "#uri")**
[HTTP request](- "#response=http(#method, #uri)"), the application
responds with [200](- "?=#response.status") status and
[application/json](- "?=#response.contentType") with  the following 
JSON in the body (formatted for readability):

<div>
<pre concordion:assert-equals="#response.body">{
  "status" : "Server Up"
}
</pre>
</div>

### ~~Example~~
