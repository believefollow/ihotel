jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ClassBakService } from '../service/class-bak.service';
import { IClassBak, ClassBak } from '../class-bak.model';

import { ClassBakUpdateComponent } from './class-bak-update.component';

describe('Component Tests', () => {
  describe('ClassBak Management Update Component', () => {
    let comp: ClassBakUpdateComponent;
    let fixture: ComponentFixture<ClassBakUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let classBakService: ClassBakService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ClassBakUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ClassBakUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClassBakUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      classBakService = TestBed.inject(ClassBakService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const classBak: IClassBak = { id: 456 };

        activatedRoute.data = of({ classBak });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(classBak));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classBak = { id: 123 };
        spyOn(classBakService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classBak });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classBak }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(classBakService.update).toHaveBeenCalledWith(classBak);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classBak = new ClassBak();
        spyOn(classBakService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classBak });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: classBak }));
        saveSubject.complete();

        // THEN
        expect(classBakService.create).toHaveBeenCalledWith(classBak);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const classBak = { id: 123 };
        spyOn(classBakService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ classBak });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(classBakService.update).toHaveBeenCalledWith(classBak);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
