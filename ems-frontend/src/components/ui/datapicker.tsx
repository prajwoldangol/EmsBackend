import React, { useState, useRef, useEffect } from 'react'
import { DayPicker } from 'react-day-picker'
import { default as defaultStyles } from 'react-day-picker/dist/style.module.css'
interface DateInputProps {
  selectedDate: Date | undefined
  onDateChange: (date: Date | undefined) => void
}
const DateInput: React.FC<DateInputProps> = ({
  selectedDate,
  onDateChange,
}) => {
  //   const [selectedDate, setSelectedDate] = useState<Date | undefined>(undefined)
  const [isPickerOpen, setIsPickerOpen] = useState<boolean>(false)
  const inputRef = useRef<HTMLDivElement>(null)

  const handleDateChange = (date: Date | undefined) => {
    // setSelectedDate(date)
    onDateChange(date)
    setIsPickerOpen(false)
  }

  const handleInputFocus = () => {
    setIsPickerOpen(true)
  }

  const handleClickOutside = (event: MouseEvent) => {
    if (inputRef.current && !inputRef.current.contains(event.target as Node)) {
      setIsPickerOpen(false)
    }
  }

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside)
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [])

  return (
    <div ref={inputRef} style={{ position: 'relative' }}>
      <input
        type='text'
        value={selectedDate ? selectedDate.toLocaleDateString() : ''}
        onFocus={handleInputFocus}
        readOnly
        placeholder='Select a date'
        className='my-2 flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50'
      />
      {isPickerOpen && (
        <div style={{ position: 'absolute', zIndex: 1000 }}>
          <DayPicker
            mode='single'
            selected={selectedDate}
            onSelect={handleDateChange}
            classNames={defaultStyles}
          />
        </div>
      )}
    </div>
  )
}

export default DateInput
