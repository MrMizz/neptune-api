# An Opinionated API that talks to your data in AWS Neptune with Scala Gremlin

If you're like me, you think Graph Databases are cool, and probably have researched their practical use.
For me, I've found that AWS Neptune is the quickest way to having fun with Graphs.

```aidl
"Just supply your Neptune Cluster Endpoint to ArgParser with AbstractMain"
```
## Sample usage
```aidl
java -jar jars/we-poli-graph/latest/we-poli-graph-assembly-0.0.1-SNAPSHOT.jar query --vertex-ids 4032920051055622454 --vertex-ids 4051820041038596757 --direction in --aggregation and
```

## This API provides the following features
* All graph queries are Vertex Id oriented  
    * Vertex & Edge Property Data lives in another DB, like Dynamo
    * Your UI can present Names, Addresses, or other Uniquely Identifying information, that you can then compose to query for a Vertex Id
* Specify a direction that you'd like to search, starting at your argument Vertex Id
    * In or Out, only the first degree (depth of 1) for now
* Pass an array of vertex ids as your origin
    * you'll need to pass an aggregation
    * And / Or
        * And (Intersection), vertices that point (In/Out) to all of the vertex ids specified 
        * Or (Union), vertices that point (In/Out) to at least one the vertex ids specified

## Deployment Considerations
Here's what has worked for me
* Your Neptune Cluster sits in a private VPC
    * for dev/test environment, you can publish this application to a small EC2 instance that sits in the same private VPC as your cluster
* Deploy this application with Lambda, or whatever you prefer, within the same private VPC as your Neptune Cluster
* Set up your NAT Gateway, Routing tables, and API Gateway, such that your endpoint can talk to the outside world
* Build a UI or Viz tool
* Have fun


