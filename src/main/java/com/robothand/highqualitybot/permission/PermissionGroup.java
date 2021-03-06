package com.robothand.highqualitybot.permission;

import com.robothand.highqualitybot.Utils;
import com.robothand.highqualitybot.command.Command;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * PermissionGroup.java
 *
 * Represents a set of permissions for a group of Users
 */
public class PermissionGroup {
    Logger log = LoggerFactory.getLogger(this.getClass());
    private ArrayList<Command> allowed;
    private ArrayList<Command> disallowed;
    private boolean allowedHasPrecedence;
    private int priority;
    private String role;

    public PermissionGroup(String filename) throws FileNotFoundException {
        // default values
        allowed = new ArrayList<>();
        disallowed = new ArrayList<>();
        allowedHasPrecedence = true;
        priority = 0;

        log.debug("Reading PermissionGroup config from \'{}\'...", filename);
        Hashtable<String,String> table = Utils.readCFG(filename);

        for (String key : table.keySet()) {
            String value = table.get(key);

            switch (key) {
                case "allowed":
                    allowed = PermissionManager.instance().parseCommandList(value);
                    break;

                case "disallowed":
                    disallowed = PermissionManager.instance().parseCommandList(value);

                case "allowedHasPrecedence":
                    allowedHasPrecedence = Boolean.parseBoolean(value);
                    break;

                case "priority":
                    priority = Integer.parseInt(value);
                    break;

                case "roleName":
                    role = value;
                    break;

                default:
                    log.info("in \'{}\': unknown property \'{}\', ignoring...", filename, key);
                    break;
            }
        }
    }

    public boolean appliesTo(Member member, Command command) {
        // check if this role applies to given command first
        if (!(allowed.contains(command) || disallowed.contains(command))) {
            return false;
        }

        boolean flag = false;

        // check by role name
        if (role != null) {
            for (Role r : member.getGuild().getRolesByName(role, true)) {
                if (member.getRoles().contains(r)) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    public boolean hasPermission(Command command) {
        boolean permission = false;

        if (allowed.contains(command)) {
            permission = true;
        }

        if (disallowed.contains(command) && !allowedHasPrecedence) {
            permission = false;
        }

        return permission;
    }

    public int getPriority() {
        return priority;
    }
}
