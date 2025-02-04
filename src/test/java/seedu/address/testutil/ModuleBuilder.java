package seedu.address.testutil;

import seedu.address.model.tamodule.AcademicYear;
import seedu.address.model.tamodule.ModuleCode;
import seedu.address.model.tamodule.ModuleName;
import seedu.address.model.tamodule.TaModule;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_NAME = "Software Engineering";
    public static final String DEFAULT_MODULE_CODE = "CS2103T";
    public static final String DEFAULT_ACADEMIC_YEAR = "21S1";

    private ModuleName moduleName;
    private ModuleCode moduleCode;
    private AcademicYear academicYear;

    /**
     * Creates a {@code ModuleBuilder} with the default details.
     */
    public ModuleBuilder() {
        moduleName = new ModuleName(DEFAULT_MODULE_NAME);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        academicYear = new AcademicYear(DEFAULT_ACADEMIC_YEAR);
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(TaModule moduleToCopy) {
        moduleName = moduleToCopy.getModuleName();
        moduleCode = moduleToCopy.getModuleCode();
        academicYear = moduleToCopy.getAcademicYear();
    }

    /**
     * Sets the {@code ModuleName} of the {@code TaModule} that we are building.
     */
    public ModuleBuilder withModuleName(String moduleName) {
        this.moduleName = new ModuleName(moduleName);
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code TaModule} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code AcademicYear} of the {@code Module} that we are building.
     */
    public ModuleBuilder withAcademicYear(String academicYear) {
        this.academicYear = new AcademicYear(academicYear);
        return this;
    }

    public TaModule build() {
        return new TaModule(moduleName, moduleCode, academicYear);
    }

}
