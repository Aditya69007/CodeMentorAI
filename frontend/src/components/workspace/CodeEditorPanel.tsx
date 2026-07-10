import Editor from "@monaco-editor/react";

import {
  FiCode,
  FiMinus,
  FiPlus,
} from "react-icons/fi";

type Language = "CPP" | "JAVA" | "PYTHON";

interface Props {
  language: Language;
  code: string;
  fontSize: number;
  submitting: boolean;

  onLanguageChange: (
    language: Language
  ) => void;

  onCodeChange: (
    code: string
  ) => void;

  onFontSizeChange: (
    size: number
  ) => void;
}

export default function CodeEditorPanel({
  language,
  code,
  fontSize,
  submitting,
  onLanguageChange,
  onCodeChange,
  onFontSizeChange,
}: Props) {
  return (
    <div className="flex h-full min-h-0 flex-col">
      <div className="app-surface app-border flex h-11 shrink-0 items-center justify-between border-b px-3">
        <div className="flex items-center gap-2">
          <FiCode className="text-emerald-500" />

          <span className="text-sm font-semibold">
            Code
          </span>
        </div>
      </div>

      <div className="app-surface app-border flex h-11 shrink-0 items-center justify-between border-b px-3">
        <select
          value={language}
          disabled={submitting}
          onChange={(event) => {
            const value = event.target.value;

            if (
              value === "CPP" ||
              value === "JAVA" ||
              value === "PYTHON"
            ) {
              onLanguageChange(value);
            }
          }}
          className="app-surface-secondary rounded-md px-3 py-1.5 text-sm outline-none"
        >
          <option value="CPP">C++</option>
          <option value="JAVA">Java</option>
          <option value="PYTHON">Python</option>
        </select>

        <div className="flex items-center gap-1">
          <button
            onClick={() =>
              onFontSizeChange(
                Math.max(fontSize - 1, 12)
              )
            }
            className="app-hover rounded-md p-2"
            title="Decrease font size"
          >
            <FiMinus />
          </button>

          <span className="app-text-muted min-w-8 text-center text-xs">
            {fontSize}
          </span>

          <button
            onClick={() =>
              onFontSizeChange(
                Math.min(fontSize + 1, 24)
              )
            }
            className="app-hover rounded-md p-2"
            title="Increase font size"
          >
            <FiPlus />
          </button>
        </div>
      </div>

      <div className="min-h-0 flex-1 bg-[#1e1e1e]">
        <Editor
          height="100%"
          theme="vs-dark"
          language={
            language === "CPP"
              ? "cpp"
              : language === "JAVA"
                ? "java"
                : "python"
          }
          value={code}
          onChange={(value) =>
            onCodeChange(value ?? "")
          }
          options={{
            fontSize,
            minimap: {
              enabled: false,
            },
            automaticLayout: true,
            scrollBeyondLastLine: false,
            wordWrap: "on",
            tabSize: 4,
            padding: {
              top: 16,
            },
          }}
        />
      </div>
    </div>
  );
}