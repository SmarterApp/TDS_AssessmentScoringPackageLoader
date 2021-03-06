package tds.testpackage.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.auto.value.AutoValue;
import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A parameter for a computation rule
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_Parameter.Builder.class)
public abstract class Parameter {
    @XmlAttribute
    public abstract String getId();
    @XmlAttribute
    public abstract String getName();
    @XmlAttribute
    public abstract String getType();
    @XmlAttribute
    public abstract int getPosition();

    @XmlElement(name="Value", type=Value.class)
    public abstract List<Value> getValues();

    @Nullable
    protected abstract List<Property> getProperties();

    @XmlElement(name="Property", type=Property.class)
    @JsonProperty(value = "properties")
    public List<Property> properties() {
        return Optional.ofNullable(getProperties()).orElse(new ArrayList<>());
    }

    public static Builder builder() {
        return new AutoValue_Parameter.Builder();
    }

    @AutoValue.Builder
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public abstract static class Builder {
        public abstract Builder setId(String newId);

        public abstract Builder setName(String newName);

        public abstract Builder setType(String newType);

        public abstract Builder setPosition(int newPosition);

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Value")
        public abstract Builder setValues(List<Value> newValues);

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Property")
        public abstract Builder setProperties(List<Property> newProperties);

        public abstract Parameter build();
    }
}
