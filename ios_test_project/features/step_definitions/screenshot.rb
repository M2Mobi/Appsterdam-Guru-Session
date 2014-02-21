def get_screenshot_name(options={:directory => nil, :filename => nil})
    directory = options[:directory] || ""
    filename = options[:filename]

    if File.directory?(directory)
        Dir.foreach(directory) do |item|
            next if item == '.' or item == '..'

            if item.include? filename
                return "#{directory.to_s}/#{item.to_s}"
            end
        end
    else
        puts "Directory [#{directory}] was not found."
    end

    puts "cannot find file [#{filename}]."
    return false
end