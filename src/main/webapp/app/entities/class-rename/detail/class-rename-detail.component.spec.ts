import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClassRenameDetailComponent } from './class-rename-detail.component';

describe('Component Tests', () => {
  describe('ClassRename Management Detail Component', () => {
    let comp: ClassRenameDetailComponent;
    let fixture: ComponentFixture<ClassRenameDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ClassRenameDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ classRename: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ClassRenameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClassRenameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load classRename on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.classRename).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
