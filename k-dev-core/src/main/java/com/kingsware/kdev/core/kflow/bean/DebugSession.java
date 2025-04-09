package com.kingsware.kdev.core.kflow.bean;

/**
 * DebugSession
 */
public class DebugSession  {


    private String uid; // Unique identifier for the node
    private String nodeId; // ID of the node
    private String status; // Status of the node

    // Getter and Setter for uid
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // Getter and Setter for nodeId
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Node{" +
                "uid='" + uid + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
