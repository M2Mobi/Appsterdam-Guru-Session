require 'chunky_png'
require 'fileutils'
include ChunkyPNG::Color

Given(/^I am logged in$/) do
    macro %Q|I am on the "Login" screen|
    macro %Q|I clear input field with id "login_username"|
    macro %Q|I enter text "guru" into field with id "login_username"|
    macro %Q|I clear input field with id "login_password"|
    macro %Q|I enter text "secret" into field with id "login_password"|
    macro %Q|I press the "Login" button|
end

Then /^I scroll until I see the "([^\"]*)" text$/ do |text|
    q = query("TextView text:'#{text}'")

    while q.empty?
        performAction('scroll_down')
        q = query("TextView text:'#{text}'")
    end
end

Given(/^I am on the "(.*?)" screen$/) do |screen_name|
    wait_for(
        :timeout => 5,
        :timeout_message => "timeout; screen ['#{screen_name}'] not found."
    ){
        element_exists("* marked:'#{screen_name}'")
    }
end

Then(/^the current screen should resemble the screenshot "(.*?)"$/) do |filename|
    puts "build number from ARG: #{ENV['BUILD_NUMBER']}"

    base_dir = "cucumber_screenshots/"

    previous_number = ENV['BUILD_NUMBER'].to_i - 1
    version_to_delete_number = (ENV['BUILD_NUMBER'].to_i - 2).to_s

    ## -------------------------------------------------- ##

    ## Make screenshot of this view ##
    Dir.mkdir(base_dir + ENV['BUILD_NUMBER']) unless File.exists?(base_dir + ENV['BUILD_NUMBER'])
    filename_created = screenshot({:prefix => "#{base_dir}#{ENV['BUILD_NUMBER']}/", :name=> "#{filename}.png"})

    previous_filename = get_screenshot_name({:directory => base_dir + previous_number.to_s, :filename => filename })

    ## -------------------------------------------------- ##

    if File.exists?(base_dir + version_to_delete_number)
        FileUtils.rm_r base_dir + version_to_delete_number
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
        # puts "pixels changed (%): #{threshold}%"

        ## Mark the difference ##
        if threshold > 0
            x, y = diff.map{ |xy| xy[0] }, diff.map{ |xy| xy[1] }

            images.last.rect(x.min, y.min, x.max, y.max, ChunkyPNG::Color.rgb(0,255,0))
            diff_image_path = images.last.save("#{base_dir} #{ENV['BUILD_NUMBER']}/#{filename}_diff.png")

            embed(diff_image_path.path, "image/png", "File changed #{threshold}%" || File.basename(diff_image_path.path))
        end
    end

end