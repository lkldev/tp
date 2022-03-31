package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.entity.EntityType;
import seedu.address.model.student.NameContainsKeywordsPredicate;

//@@author wxliong
/**
 * Finds and lists all students in TAssist whose name contains any of the argument keywords.
 * The search is case-insensitive. e.g hans will match Hans.
 * The order of the keywords does not matter. e.g. Hans Bo will match Bo Hans.
 * Only the name is searched.
 * Only full words will be matched e.g. Han will not match Hans.
 * Students matching at least one keyword will be returned e.g. Hans Bo will return Hans Gruber, Bo Yang.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all students whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... "
            + "\n\tExample: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        String result = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                model.getFilteredStudentList().size());
        List<String> assessments = model.getFilteredStudentList().stream().map(student ->
                model.getUnfilteredAssessmentList().stream()
                        .filter(assessment -> assessment.getModule().hasStudent(student))
                        .map(assessment ->
                    assessment.getAttemptOfStudent(student).map(grade ->
                            String.format("Student %s (%s)| module %s: %s: %d\n",
                                    student.getName(), student.getStudentId(), assessment.getModule().getModuleCode(),
                                    assessment.getAssessmentName(), grade.value))
                            .orElse(String.format("Student %s (%s)| has no attempt for %s: %s\n",
                                    student.getName(), student.getStudentId(), assessment.getModule().getModuleCode(),
                                    assessment.getAssessmentName())))
                        .reduce("", (x, y) -> x + y))
                .collect(Collectors.toList());
        result += "\n";
        for (String s : assessments) {
            String[] output = s.split("\\|", 2);
            String name = output[0];
            String nameRegex = name.replace("(", "\\(").replace(")", "\\)");
            String attempts = output[1].replaceAll(nameRegex, "\t");
            result += name + "\n\t|" + attempts + "\n";
        }
        return new CommandResult(result, EntityType.STUDENT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

