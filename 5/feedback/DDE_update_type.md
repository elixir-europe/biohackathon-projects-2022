# DDE Editor - Updating BioSchemas' Type
* Tutorial: https://alasdairgray.github.io/bioschemas.github.io/tutorials/dde/update_type
* Editor: https://discovery.biothings.io/editor

## Feedback
[Marcos Casado]
- Typo at **step 2.1**: "type,sSelect" and "If the type to ``be updates``"
- Error at **step 2.1**: at ``Click ‘extend’ (icon on the right at the end of the row corresponding to the type to be updated)``. If you click directly on the class after using the searchbox it says "non existing" and redirects you to the playground.
- Error at **step 2.1**: at ``Click ‘extend’ (icon on the right at the end of the row corresponding to the type to be updated)`` You cannot click on the "extend icon" after ``Search for the name of the type to be updated using the search box``, since it shows the classes without that icon. The types below do have the icon but are unfiltered.
- At **step 2.2**: same feedback of the other [feedback document](../feedback/DDE_create_type.md).
- At **step 2.3**: the content is the same as with "Create type" tutorial, although this one is to modify it instead. I would add some context regarding what "update" means, in that it is only possible to add more properties, but not to edit the ones already existing there.
- At **step 2.3**: ``Specifying the expected (range) input type`` should belong to the previous bullet point, since it appears when creating a property. I would expect it to be at the same level with "name, description, domain...". Otherwise it looks like a different action instead of part of the previous one.

- At **step 2.1**: both update and create new types tutorials only differ in the **nomenclature** of "parent" or "type to edit", but both **converge** to clicking "extend". From that point on, **no difference in the whole tutorial**. From my perspective as a user of the DDE editor, **updating and creating types are the same**: extending an already existing schema. The only allowed action to a new type is to  "add properties". From the editor's perspective there is no difference if I extend from ARN ("update type") to put it later in GH within BioChemEntity, or if I extend BioChemEntity to create a new existing type ("new type"). I see it a bit counterintuitive, since the **real difference**, in my perspective, is not on the editing process, but rather **where you upload your file afterwards in GitHub** (i.e. what file you replace with your new draft). I would recommend to create one single documentation page (explaining how to log-in and add properties to a type) and just point to it from both tutorials. And only after users have their new JSON-LD file, highlight the difference between updating or creating a new type when creating the ellaborated documentation for how to push changes to GH, requested in the other [feedback file](./DDE_create_type.md).