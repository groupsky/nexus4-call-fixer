# nexus4-call-fixer
A fix to no audo problem with nexus 4 and cyanogenmod 11 as described at [xda](http://forum.xda-developers.com/showpost.php?p=57445259&postcount=28)

The application hooks to call status and disables the CheckinService when call is active then activates it after the call is finished. REQUIRES ROOT.

Not tested on anything besides Nexus 4 with cm-11-20141115-SNAPSHOT-M12-mako.

## Installation
[Download](https://github.com/groupsky/nexus4-call-fixer/releases) the latest release and install on your Nexus 4.

Run the app and click the enable button. Then try to call. If this doesn't work, try to reboot the device.

