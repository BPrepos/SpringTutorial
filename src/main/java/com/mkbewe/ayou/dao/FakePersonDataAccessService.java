package com.mkbewe.ayou.dao;

import com.mkbewe.ayou.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao{

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonbyId(UUID id) {
        Optional<Person> personMb = selectPersonById(id);
        if (personMb.isEmpty()){
            return 0;
        }
        DB.remove((personMb.get()));
        return 1;
    }

    @Override
    public int updatePersonbyId(UUID id, Person updt) {
        return selectPersonById(id)
                .map(p -> {
                    int index = DB.indexOf(p);
                    if (index >= 0){
                        DB.set(index, new Person(id, updt.getName()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0);
    }
}
