package mvc.domain;

import javax.validation.constraints.*;

public class Person {

	@NotNull
	@Size(min = 2, max = 14)
	private String name;

	@NotNull
	@Min(0)
	private Integer age;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + getAge() + "]";
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}


}
