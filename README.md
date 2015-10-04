# Difference Utils

## This is java library for checking differences between 2 objects.

### Basic example. 
We should annotate out fields with `@Merge` annotation.

```
public class TestEntity {
    @Merge private String login;
    @Merge private String password;
}
```

Then you should call diff methods: 

Getting differences of object
```
DiffUtils.difference(testEntityInstance1, testEntityInstance2)
```

Or merging objects and getting string of changes
```
DiffUtils.merge(testEntityInstance1, testEntityInstance2, false)
```

If you want create your own format of changes you should implement `Difference` interface.
