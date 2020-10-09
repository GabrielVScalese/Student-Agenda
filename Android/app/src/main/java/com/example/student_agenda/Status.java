package com.example.student_agenda;

import java.util.concurrent.ExecutorCompletionService;

public class Status
{
    private String status;

    public Status (String status) throws Exception
    {
        setStatus (status);
    }

    public void setStatus (String status) throws Exception
    {
        if (status == null || status.equals(""))
            throw new Exception ("Status invalido");

        this.status = status;
    }

    public String getStatus ()
    {
        return this.status;
    }

    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (this.getClass() != obj.getClass())
            return false;

        Status status = (Status) obj;

        if (!this.status.equals(status.status))
            return false;

        return true;
    }

    public int hashCode ()
    {
        int ret = 17;

        ret = ret * 17 + this.status.hashCode();

        if (ret < 0)
            ret = -ret;

        return ret;
    }

    public String toString ()
    {
        return "Status: " + this.status;
    }
}
