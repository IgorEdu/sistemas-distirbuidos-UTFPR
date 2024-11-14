package com.UTFPR.commands;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface Command {
    public void execute() throws IOException;
}
