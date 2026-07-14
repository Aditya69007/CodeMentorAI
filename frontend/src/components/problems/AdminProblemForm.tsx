import {
  useState,
  type FormEvent,
  type ReactNode,
} from "react";

import {
  FiAlertCircle,
  FiBookOpen,
  FiCode,
  FiFileText,
  FiPlus,
  FiSave,
  FiTag,
  FiTrash2,
} from "react-icons/fi";

import type {
  ProblemRequest,
} from "../../types/problem";


interface AdminProblemFormProps {
  initialData?: ProblemRequest;

  submitLabel: string;

  loading?: boolean;

  error?: string;

  onSubmit: (
    data: ProblemRequest
  ) => Promise<void>;
}


const emptyExample = () => ({
  input: "",
  output: "",
  explanation: "",
  orderIndex: 1,
});


const defaultFormData: ProblemRequest = {
  title: "",
  description: "",
  difficulty: "EASY",
  constraints: "",
  inputFormat: "",
  outputFormat: "",
  sampleInput: "",
  sampleOutput: "",
  tags: [],
  examples: [
    emptyExample(),
  ],
};


export default function AdminProblemForm({
  initialData,
  submitLabel,
  loading = false,
  error = "",
  onSubmit,
}: AdminProblemFormProps) {

  const [formData, setFormData] =
    useState<ProblemRequest>(
      initialData ?? defaultFormData
    );


  const [tagInput, setTagInput] =
    useState("");


  const updateField = <
    K extends keyof ProblemRequest
  >(
    field: K,
    value: ProblemRequest[K]
  ) => {

    setFormData((current) => ({
      ...current,
      [field]: value,
    }));
  };


  const addTag = () => {

    const value =
      tagInput.trim();


    if (!value) {
      return;
    }


    const alreadyExists =
      formData.tags.some(
        (tag) =>
          tag.toLowerCase() ===
          value.toLowerCase()
      );


    if (alreadyExists) {

      setTagInput("");

      return;
    }


    updateField(
      "tags",
      [
        ...formData.tags,
        value,
      ]
    );


    setTagInput("");
  };


  const removeTag = (
    tagToRemove: string
  ) => {

    updateField(
      "tags",
      formData.tags.filter(
        (tag) =>
          tag !== tagToRemove
      )
    );
  };


  const addExample = () => {

    updateField(
      "examples",
      [
        ...formData.examples,

        {
          ...emptyExample(),

          orderIndex:
            formData.examples.length + 1,
        },
      ]
    );
  };


  const removeExample = (
    index: number
  ) => {

    if (
      formData.examples.length === 1
    ) {
      return;
    }


    const updatedExamples =
      formData.examples

        .filter(
          (_, exampleIndex) =>
            exampleIndex !== index
        )

        .map(
          (example, exampleIndex) => ({
            ...example,

            orderIndex:
              exampleIndex + 1,
          })
        );


    updateField(
      "examples",
      updatedExamples
    );
  };


  const updateExample = (

    index: number,

    field:
      | "input"
      | "output"
      | "explanation",

    value: string

  ) => {

    const updatedExamples =
      formData.examples.map(
        (example, exampleIndex) => {

          if (
            exampleIndex !== index
          ) {
            return example;
          }


          return {
            ...example,
            [field]: value,
          };
        }
      );


    updateField(
      "examples",
      updatedExamples
    );
  };


  const handleSubmit = async (
    event: FormEvent<HTMLFormElement>
  ) => {

    event.preventDefault();


    await onSubmit(formData);
  };


  return (

    <form
      onSubmit={handleSubmit}
      className="space-y-6"
    >


      {/* ERROR */}

      {error && (

        <div className="flex items-center gap-3 rounded-xl border border-red-500/30 bg-red-500/10 px-4 py-3 text-sm text-red-500">

          <FiAlertCircle size={18} />

          {error}

        </div>

      )}



      {/* BASIC INFORMATION */}

      <FormSection

        icon={<FiBookOpen />}

        title="Basic Information"

        description="Define the problem title, difficulty, and description."

      >

        <div className="grid gap-5 lg:grid-cols-[1fr_220px]">


          <FormField
            label="Problem Title"
            required
          >

            <input

              type="text"

              value={formData.title}

              onChange={(event) =>
                updateField(
                  "title",
                  event.target.value
                )
              }

              required

              placeholder="Example: Valid Parentheses"

              className="admin-input"

            />

          </FormField>


          <FormField
            label="Difficulty"
            required
          >

            <select

              value={formData.difficulty}

              onChange={(event) =>
                updateField(
                  "difficulty",
                  event.target.value as
                    ProblemRequest["difficulty"]
                )
              }

              className="admin-input"

            >

              <option value="EASY">
                Easy
              </option>

              <option value="MEDIUM">
                Medium
              </option>

              <option value="HARD">
                Hard
              </option>

            </select>

          </FormField>

        </div>


        <FormField
          label="Description"
          required
        >

          <textarea

            value={formData.description}

            onChange={(event) =>
              updateField(
                "description",
                event.target.value
              )
            }

            required

            rows={7}

            placeholder="Explain the coding problem clearly..."

            className="admin-input resize-y"

          />

        </FormField>

      </FormSection>



      {/* PROBLEM SPECIFICATION */}

      <FormSection

        icon={<FiFileText />}

        title="Problem Specification"

        description="Provide constraints and expected input and output formats."

      >

        <FormField label="Constraints">

          <textarea

            value={formData.constraints}

            onChange={(event) =>
              updateField(
                "constraints",
                event.target.value
              )
            }

            rows={4}

            placeholder="Example: 1 <= n <= 100000"

            className="admin-input resize-y font-mono text-sm"

          />

        </FormField>


        <div className="grid gap-5 lg:grid-cols-2">


          <FormField label="Input Format">

            <textarea

              value={formData.inputFormat}

              onChange={(event) =>
                updateField(
                  "inputFormat",
                  event.target.value
                )
              }

              rows={5}

              placeholder="Describe the expected input..."

              className="admin-input resize-y"

            />

          </FormField>


          <FormField label="Output Format">

            <textarea

              value={formData.outputFormat}

              onChange={(event) =>
                updateField(
                  "outputFormat",
                  event.target.value
                )
              }

              rows={5}

              placeholder="Describe the expected output..."

              className="admin-input resize-y"

            />

          </FormField>

        </div>

      </FormSection>



      {/* SAMPLE INPUT OUTPUT */}

      <FormSection

        icon={<FiCode />}

        title="Sample Input & Output"

        description="Add the primary sample shown to users on the problem page."

      >

        <div className="grid gap-5 lg:grid-cols-2">


          <FormField label="Sample Input">

            <textarea

              value={formData.sampleInput}

              onChange={(event) =>
                updateField(
                  "sampleInput",
                  event.target.value
                )
              }

              rows={7}

              placeholder="Enter sample input..."

              className="admin-input resize-y font-mono text-sm"

            />

          </FormField>


          <FormField label="Sample Output">

            <textarea

              value={formData.sampleOutput}

              onChange={(event) =>
                updateField(
                  "sampleOutput",
                  event.target.value
                )
              }

              rows={7}

              placeholder="Enter sample output..."

              className="admin-input resize-y font-mono text-sm"

            />

          </FormField>

        </div>

      </FormSection>



      {/* TAGS */}

      <FormSection

        icon={<FiTag />}

        title="Problem Tags"

        description="Add technologies, concepts, or algorithm categories."

      >

        <div className="flex gap-3">


          <input

            type="text"

            value={tagInput}

            onChange={(event) =>
              setTagInput(
                event.target.value
              )
            }

            onKeyDown={(event) => {

              if (
                event.key === "Enter"
              ) {

                event.preventDefault();

                addTag();
              }

            }}

            placeholder="Example: Stack"

            className="admin-input"

          />


          <button

            type="button"

            onClick={addTag}

            className="inline-flex shrink-0 items-center gap-2 rounded-lg bg-blue-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-blue-500"

          >

            <FiPlus />

            Add Tag

          </button>

        </div>


        {formData.tags.length > 0 && (

          <div className="flex flex-wrap gap-2">

            {formData.tags.map(
              (tag) => (

                <span

                  key={tag}

                  className="app-surface-secondary app-border inline-flex items-center gap-2 rounded-full border px-3 py-1.5 text-sm"

                >

                  {tag}


                  <button

                    type="button"

                    onClick={() =>
                      removeTag(tag)
                    }

                    className="app-text-muted transition hover:text-red-500"

                  >

                    ×

                  </button>

                </span>

              )
            )}

          </div>

        )}

      </FormSection>



      {/* EXAMPLES */}

      <FormSection

        icon={<FiCode />}

        title="Problem Examples"

        description="Create detailed examples that help users understand the problem."

      >

        <div className="space-y-5">


          {formData.examples.map(
            (example, index) => (

              <div

                key={index}

                className="app-surface-secondary app-border rounded-xl border p-5"

              >


                <div className="mb-5 flex items-center justify-between gap-4">


                  <div>

                    <h3 className="font-semibold">

                      Example {index + 1}

                    </h3>


                    <p className="app-text-muted mt-1 text-xs">

                      Order index:{" "}

                      {example.orderIndex}

                    </p>

                  </div>


                  <button

                    type="button"

                    onClick={() =>
                      removeExample(index)
                    }

                    disabled={
                      formData.examples.length === 1
                    }

                    className="inline-flex h-9 w-9 items-center justify-center rounded-lg text-red-500 transition hover:bg-red-500/10 disabled:cursor-not-allowed disabled:opacity-30"

                    title="Remove example"

                  >

                    <FiTrash2 />

                  </button>

                </div>



                <div className="grid gap-5 lg:grid-cols-2">


                  <FormField
                    label="Input"
                    required
                  >

                    <textarea

                      value={example.input}

                      onChange={(event) =>
                        updateExample(
                          index,
                          "input",
                          event.target.value
                        )
                      }

                      required

                      rows={6}

                      placeholder="Example input..."

                      className="admin-input resize-y font-mono text-sm"

                    />

                  </FormField>


                  <FormField
                    label="Output"
                    required
                  >

                    <textarea

                      value={example.output}

                      onChange={(event) =>
                        updateExample(
                          index,
                          "output",
                          event.target.value
                        )
                      }

                      required

                      rows={6}

                      placeholder="Expected output..."

                      className="admin-input resize-y font-mono text-sm"

                    />

                  </FormField>

                </div>


                <FormField label="Explanation">

                  <textarea

                    value={example.explanation}

                    onChange={(event) =>
                      updateExample(
                        index,
                        "explanation",
                        event.target.value
                      )
                    }

                    rows={4}

                    placeholder="Explain why this output is produced..."

                    className="admin-input resize-y"

                  />

                </FormField>


              </div>

            )
          )}


          <button

            type="button"

            onClick={addExample}

            className="app-border app-hover app-text-secondary flex w-full items-center justify-center gap-2 rounded-xl border border-dashed px-4 py-4 text-sm font-semibold transition"

          >

            <FiPlus />

            Add Another Example

          </button>

        </div>

      </FormSection>



      {/* SUBMIT */}

      <div className="app-border flex items-center justify-end border-t pt-6">


        <button

          type="submit"

          disabled={loading}

          className="inline-flex min-w-40 items-center justify-center gap-2 rounded-lg bg-blue-600 px-5 py-3 text-sm font-semibold text-white transition hover:bg-blue-500 disabled:cursor-not-allowed disabled:opacity-60"

        >

          <FiSave />


          {loading
            ? "Saving..."
            : submitLabel}

        </button>

      </div>


    </form>
  );
}



interface FormSectionProps {
  icon: ReactNode;
  title: string;
  description: string;
  children: ReactNode;
}


function FormSection({
  icon,
  title,
  description,
  children,
}: FormSectionProps) {

  return (

    <section className="app-surface app-border overflow-hidden rounded-xl border">


      <div className="app-border flex items-start gap-3 border-b px-6 py-5">

        <div className="mt-0.5 text-blue-500">

          {icon}

        </div>


        <div>

          <h2 className="font-semibold">

            {title}

          </h2>


          <p className="app-text-secondary mt-1 text-sm">

            {description}

          </p>

        </div>

      </div>


      <div className="space-y-5 p-6">

        {children}

      </div>


    </section>

  );
}



interface FormFieldProps {
  label: string;
  required?: boolean;
  children: ReactNode;
}


function FormField({
  label,
  required = false,
  children,
}: FormFieldProps) {

  return (

    <label className="block">

      <span className="mb-2 block text-sm font-medium">

        {label}

        {required && (

          <span className="ml-1 text-red-500">

            *

          </span>

        )}

      </span>


      {children}

    </label>

  );
}