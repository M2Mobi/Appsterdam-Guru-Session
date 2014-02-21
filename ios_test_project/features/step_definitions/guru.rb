require 'chunky_png'
require 'fileutils'
include ChunkyPNG::Color

Given(/^I am on the "(.*?)" screen$/) do |screen_name|
    wait_for(
        :timeout => 5,
        :timeout_message => "timeout; screen ['#{screen_name}'] not found."
    ){
        element_exists("* marked:'#{screen_name}'")
    }
end

Then(/^the current screen should resemble the screenshot "(.*?)"$/) do |filename|
    puts "build number from ARG: #{ENV['TEST']}"

    previous_number = ENV['BUILD_NUMBER'].to_i - 1
    version_to_delete_number = (ENV['BUILD_NUMBER'].to_i - 2).to_s

    ## -------------------------------------------------- ##

    ## Make screenshot of this view ##
    Dir.mkdir(ENV['BUILD_NUMBER']) unless File.exists?(ENV['BUILD_NUMBER'])
    filename_created = screenshot({:prefix => "#{ENV['BUILD_NUMBER']}/", :name=> "#{filename}.png"})

    previous_filename = get_screenshot_name({:directory => previous_number.to_s, :filename => filename })
    ## -------------------------------------------------- ##

    if File.exists?(version_to_delete_number)
        FileUtils.rm_r version_to_delete_number
        puts "deleting folder [#{version_to_delete_number}]"
    end

    ## -------------------------------------------------- ##

    if previous_filename
        images = [
          ChunkyPNG::Image.from_file(filename_created),
          ChunkyPNG::Image.from_file(previous_filename)
        ]

        diff = []

        images.first.height.times do |y|
            y += 50

            if y < images.first.height.to_i
                ## puts "y: #{y}"

                images.first.row(y).each_with_index do |pixel, x|
                    diff << [x,y] unless pixel == images.last[x,y]
                end
            end
        end

        ## difference in the image - percentage change in the image ##
        threshold = (diff.length.to_f / images.first.pixels.length) * 100

        # puts "pixels (total):     #{images.first.pixels.length}"
        # puts "pixels changed:     #{diff.length}"
        puts "pixels changed (%): #{threshold}%"

        ## Mark the difference ##
        if threshold > 0
            x, y = diff.map{ |xy| xy[0] }, diff.map{ |xy| xy[1] }

            images.last.rect(x.min, y.min, x.max, y.max, ChunkyPNG::Color.rgb(0,255,0))
            diff_image_path = images.last.save("#{ENV['BUILD_NUMBER']}/#{filename}_diff.png")

            embed(diff_image_path.path, "image/png", "File changed #{threshold}%" || File.basename(diff_image_path.path))
        end
    end

end