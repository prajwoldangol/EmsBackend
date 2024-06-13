import React from 'react'
import { useFormContext, Controller } from 'react-hook-form'

interface TimeInputProps {
  name: string
  label: string
  disabled?: boolean

  onChange?: (time: string) => void
}

const TimeInput: React.FC<TimeInputProps> = ({
  name,
  label,

  disabled,
  onChange,
}) => {
  const { control } = useFormContext()

  return (
    <div>
      <label htmlFor={name}>{label}</label>
      <Controller
        name={name}
        control={control}
        render={({ field }) => (
          <input
            {...field}
            type='time'
            disabled={disabled}
            className='w-1full my-2 flex h-9 rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
            onChange={(e) => {
              // field.onChange(e.target.value) // Update form value
              onChange && onChange(e.target.value) // Call onChange prop
            }}
          />
        )}
      />
    </div>
  )
}

export default TimeInput
