package ru.belozerova.addressbook.model;
// коллекция из групп
import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Groups extends ForwardingSet<GroupData> {
    private Set<GroupData> delegate;

    public Groups(Groups groups) {
    this.delegate = new HashSet<GroupData>(groups.delegate); //ооздаем 2й объект, чтобы с обоими сразу работать
    }

    public Groups() {
        this.delegate = new HashSet<GroupData>();
    }

    public Groups(Collection<GroupData> groups) {
        this.delegate = new HashSet<GroupData>(groups);
    }

    @Override
    protected Set<GroupData> delegate() {
        return delegate;
    }

    public Groups withAdded(GroupData group){
        Groups groups = new Groups(this); //вызываем конструктор
        groups.add(group);
        return groups;
    }
    public Groups without(GroupData group){
        Groups groups = new Groups(this); //вызываем конструктор
        groups.remove(group);
        return groups;
    }

}
