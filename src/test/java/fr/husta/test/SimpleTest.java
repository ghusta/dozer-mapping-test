package fr.husta.test;

import com.github.dozermapper.core.Mapper;
import fr.husta.test.dto.MyPojoDto;
import fr.husta.test.model.MyNestedPojoBean;
import fr.husta.test.model.MyPojoPart1Bean;
import fr.husta.test.model.MyPojoPart2Bean;
import fr.husta.test.utils.MapperUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest {

    private Mapper mapper = MapperUtils.getMapper();

    @Test
    public void mapWithOverwriting() {
        MyPojoPart1Bean part1Bean = new MyPojoPart1Bean();
        part1Bean.setName("Toto");
        MyPojoDto destDto;

        // new instance created
        destDto = mapper.map(part1Bean, MyPojoDto.class);

        assertThat(destDto).isNotNull();
        assertThat(destDto.getName()).isNotEmpty();

        MyPojoPart2Bean part2Bean = new MyPojoPart2Bean();
        part2Bean.setAge(10);
        part2Bean.setDob(LocalDate.of(2000, Month.AUGUST, 31));

        // overwritten by new instance
        destDto = mapper.map(part2Bean, MyPojoDto.class);
        assertThat(destDto).isNotNull();
        assertThat(destDto.getName()).isNull();
        assertThat(destDto.getAge()).isEqualTo(10);
        assertThat(destDto.getDob()).isEqualTo("2000-08-31");
    }

    @Test
    public void mapWithOverwritingUsingMapId() {
        MyPojoPart1Bean part1Bean = new MyPojoPart1Bean();
        part1Bean.setName("Toto");
        MyNestedPojoBean nestedBean = new MyNestedPojoBean();
        nestedBean.setDescription2("Hello");
        part1Bean.setMyNestedPojoBean(nestedBean);

        MyPojoDto destDto;

        // new instance created
        destDto = mapper.map(part1Bean, MyPojoDto.class, "special-mapping-nested-beans");

        assertThat(destDto).isNotNull();
        assertThat(destDto.getName()).isNotEmpty();
        assertThat(destDto.getMyNestedPojoDto()).isNotNull();
        assertThat(destDto.getMyNestedPojoDto().getDescription()).isEqualTo("Hello");
        assertThat(destDto.getMyNestedPojoDto().getLabel()).isNull();
    }

    @Test
    public void mapNestedPojoUsingDefaultMapping() {
        MyPojoPart1Bean part1Bean = new MyPojoPart1Bean();
        part1Bean.setName("Toto");
        MyNestedPojoBean nestedBean = new MyNestedPojoBean();
        nestedBean.setDescription2("Hello");
        part1Bean.setMyNestedPojoBean(nestedBean);

        MyPojoDto destDto;

        // new instance created
        destDto = mapper.map(part1Bean, MyPojoDto.class);

        assertThat(destDto).isNotNull();
        assertThat(destDto.getName()).isNotEmpty();
        assertThat(destDto.getMyNestedPojoDto()).isNotNull();
        assertThat(destDto.getMyNestedPojoDto().getDescription()).isNull();
        assertThat(destDto.getMyNestedPojoDto().getLabel()).isNull();
    }

    @Test
    public void mapCumulative() {
        MyPojoDto destDto = new MyPojoDto();
        MyPojoPart1Bean part1Bean = new MyPojoPart1Bean();
        part1Bean.setName("Toto");

        // use existing instance
        mapper.map(part1Bean, destDto);

        assertThat(destDto).isNotNull();
        assertThat(destDto.getName()).isEqualTo("Toto");

        MyPojoPart2Bean part2Bean = new MyPojoPart2Bean();
        part2Bean.setAge(10);
        part2Bean.setDob(LocalDate.of(2000, Month.AUGUST, 31));

        // use existing instance (not overwritten)
        mapper.map(part2Bean, destDto);

        assertThat(destDto).isNotNull();
        assertThat(destDto.getName()).isEqualTo("Toto");
        assertThat(destDto.getAge()).isEqualTo(10);
        assertThat(destDto.getDob()).isEqualTo("2000-08-31");
    }

}
